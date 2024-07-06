package org.presearch.model;

import com.fasterxml.jackson.annotation.JsonSetter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetaDTO {

    private String description;
    private String url;
    private String serverDescription;
    private String serverUrl;
    private String gatewayPool;
    @JsonSetter("remote_addr")
    private String remoteAddress;
    private String version;

}
