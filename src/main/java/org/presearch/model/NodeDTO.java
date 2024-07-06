package org.presearch.model;


import org.presearch.domain.NodeKey;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NodeDTO {

    @JsonAlias(NodeKey.NODE_1)
    public PublicKeyDTO publicKey;
}
