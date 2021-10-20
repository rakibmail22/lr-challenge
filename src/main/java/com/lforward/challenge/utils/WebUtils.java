package com.lforward.challenge.utils;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * @author bashir
 * @since 21/10/21.
 */
public class WebUtils {

    public static URI getLocation(String uuid) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{uuid}")
                .buildAndExpand(uuid)
                .toUri();
    }
}
