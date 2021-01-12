package com.geekerit.hexo_blog_web_hooks.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;

/**
 * @author geekerhub
 * @date 21/4/2020 上午10:57
 */
@RestController
@RequestMapping(value = "/blog")
@Slf4j
public class WebHookController {

    @Value("${docker.localRepositoryPath}")
    private String localRepositoryPath;

    private final String[] COMMAND_ARRAY = new String[4];

    @RequestMapping(value = "/webHooks")
    public void webHooks() {
        try {
            String[] commandArray = buildCommandArray();
            log.info("command [{}] will be executed", Arrays.toString(commandArray));
            Process exec = Runtime.getRuntime().exec(commandArray);
            try {
                exec.waitFor();
            } catch (InterruptedException e) {
                log.warn("wait process throw exception", e);
            }
            log.info("exec pull process info is {}", exec);
        } catch (IOException e) {
            log.warn("run web hooks throw exception and detail is {}", e.getMessage());
            e.printStackTrace();
        }
    }

    private String[] buildCommandArray() {
        String bash = "/bin/bash";
        COMMAND_ARRAY[0] = bash;
        String toParam = "-c";
        COMMAND_ARRAY[1] = toParam;
        /**
         * <b>[Note]</b>
         * <p>1.git required set ssh_key otherwise need login github account after exec git pull command</p>
         * <p>2.replace dir path with yours github repository on your container</p>
         */
        String CHANGE_DIR = "git -C " + localRepositoryPath + " pull";
        COMMAND_ARRAY[2] = CHANGE_DIR;
        String hexoClean = "hexo clean";
        COMMAND_ARRAY[3] = hexoClean;
        return COMMAND_ARRAY;
    }
}
