package com.monkeydp.biz.spring.ext.spring.http

import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_OCTET_STREAM
import org.springframework.http.ResponseEntity

/**
 * @author iPotato-Work
 * @date 2021/1/19
 */
object ResponseFile {
    fun create(resource: Resource) =
            ResponseEntity.ok()
                    .contentType(APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"${resource.file.name}\"")
                    .body(resource)
}
