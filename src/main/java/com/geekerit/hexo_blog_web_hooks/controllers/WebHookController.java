package com.geekerit.hexo_blog_web_hooks.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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

    private static final String BASH = "/bin/bash";
    private static final String TO_PARAM = "-c";
    private static final String CHANGE_DIR = "git -C /root/blog_sources/HexoBlog/ pull";
    private static final String[] COMMAND_ARRAY = new String[3];

    static {
        COMMAND_ARRAY[0] = BASH;
        COMMAND_ARRAY[1] = TO_PARAM;
        COMMAND_ARRAY[2] = CHANGE_DIR;
    }


    @RequestMapping(value = "/webHooks")
    public void webHooks() {
        try {
            Process exec = Runtime.getRuntime().exec(COMMAND_ARRAY);
            log.info("exec pull process info is {}", exec);
        } catch (IOException e) {
            log.warn("run web hooks throw exception and detail is {}", e.getMessage());
            e.printStackTrace();
        }
    }
}
