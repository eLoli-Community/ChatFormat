package com.eloli.chatformat;

import com.eloli.chatformat.message.components.Component;
import com.eloli.chatformat.message.IChatEvent;
import com.eloli.chatformat.models.ILoader;
import com.eloli.chatformat.models.IPlayer;
import org.openjdk.nashorn.api.scripting.NashornScriptEngine;
import org.openjdk.nashorn.api.scripting.NashornScriptEngineFactory;

import javax.script.CompiledScript;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Core {
    private NashornScriptEngineFactory factory;
    private NashornScriptEngine engine;
    private ILoader loader;
    private Map<String,CompiledScript> compiledScripts;
    public Core(ILoader loader){
        this.loader = loader;
        if(!loader.getConfigPath().toFile().exists()){
            loader.getConfigPath().toFile().mkdirs();
        }
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        factory = (NashornScriptEngineFactory) scriptEngineManager.getEngineByName("js").getFactory();
    }
    public Component callOnChat(IChatEvent e) throws ScriptException, NoSuchMethodException {
        engine.put("e",e);
        return (Component) engine.invokeFunction("onChat");
    }

    public void init() throws ScriptException, IOException {
        compiledScripts = new HashMap<>();
        engine = (NashornScriptEngine) factory.getScriptEngine(getClass().getClassLoader());
        engine.put("__core",this);
        InputStream inputStream = Core.class.getResourceAsStream("/chatformat/init.js");
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        engine.eval(inputStreamReader);
        inputStreamReader.close();
        inputStream.close();
        require("index");
    }

    public Object require(String fileName) throws ScriptException, IOException {
        fileName = fileName + ".js";
        CompiledScript compiledScript = compiledScripts.get(fileName);
        if(compiledScript == null){
            File requireFile = loader.getConfigPath().resolve(Path.of(".",fileName.split("/"))).toFile();
            compiledScript = engine.compile(new FileReader(requireFile, StandardCharsets.UTF_8));
            compiledScripts.put(fileName,compiledScript);
        }
        return compiledScript.eval();
    }

    public String apply(IPlayer player, String text){
        return loader.replace(player, text);
    }
   }
