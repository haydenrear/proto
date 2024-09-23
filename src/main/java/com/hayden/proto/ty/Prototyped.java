package com.hayden.proto.ty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hayden.proto.proto.Prototype;

public interface Prototyped<P extends Prototype> {

    @JsonIgnore
    P proto();

}
