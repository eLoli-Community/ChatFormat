package com.eloli.chatformat;

import com.eloli.chatformat.channel.ChatPackage;
import com.eloli.chatformat.channel.ReloadPackage;
import com.eloli.chatformat.message.IChatEvent;
import com.eloli.chatformat.message.components.Component;
import com.eloli.chatformat.models.ILoader;
import com.eloli.chatformat.models.IPlayer;
import com.eloli.sodioncore.channel.MessageChannel;
import com.eloli.sodioncore.channel.util.ByteUtil;
import org.openjdk.nashorn.api.scripting.NashornScriptEngine;
import org.openjdk.nashorn.api.scripting.NashornScriptEngineFactory;

import javax.script.CompiledScript;
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
    private Map<String, CompiledScript> compiledScripts;
    private MessageChannel channel;

    public Core(ILoader loader) {
        this.loader = loader;
        if (!loader.getConfigPath().toFile().exists()) {
            loader.getConfigPath().toFile().mkdirs();
        }
        factory = new NashornScriptEngineFactory();
        channel = new MessageChannel("chatformat:event", ByteUtil.sha256(new byte[0]),ByteUtil.sha256(new byte[0]));
        channel.registerServerPacket(ChatPackage.class)
                .registerClientPacket(ReloadPackage.class);
    }

    public Component callOnChat(IChatEvent e) throws ScriptException, NoSuchMethodException {
        engine.put("e", e);
        return (Component) engine.invokeFunction("onChat");
    }

    public MessageChannel getChannel(){
        return channel;
    }

    public ILoader getLoader(){
        return loader;
    }

    public void init() throws ScriptException, IOException {
        compiledScripts = new HashMap<>();
        engine = (NashornScriptEngine) factory.getScriptEngine(getClass().getClassLoader());
        engine.put("__core", this);
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
        if (compiledScript == null) {
            Path p = loader.getConfigPath();
            for (String s : fileName.split("/")) {
                p = p.resolve(s);
            }
            File requireFile = p.toFile();
            InputStream inputStream = new FileInputStream(requireFile);
            Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            compiledScript = engine.compile(reader);
            reader.close();
            inputStream.close();
            compiledScripts.put(fileName, compiledScript);
        }
        return compiledScript.eval();
    }

    public String apply(IPlayer player, String text) {
        return loader.replace(player, text);
    }
}