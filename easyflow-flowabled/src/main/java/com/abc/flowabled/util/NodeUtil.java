package com.abc.flowabled.util;

import com.abc.flowabled.domain.dto.NodeDTO;

import java.util.Objects;

public class NodeUtil {

    public static Boolean isNode(NodeDTO nodeDTO) {
        return Objects.nonNull(nodeDTO);
    }
}
