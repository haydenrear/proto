package com.hayden.proto.prototyped.sources.data.inputs.request;

import com.hayden.proto.prototype.datasource.data.inputs.request.PathContractProto;

public @interface Path {

    /**
     * May seem weird but consider ant matcher - what if they make it too long!
     * Maybe one of them is length of path, maybe one is special characters, etc.
     * @return
     */
    Class<? extends PathContractProto>[] proto() default {};

    // TODO: path framework adapter - based on protocol - mapping from protocol to framework,
    //          i.e. REST to Spring??? 

}