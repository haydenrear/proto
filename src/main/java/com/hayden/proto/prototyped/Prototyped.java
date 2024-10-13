package com.hayden.proto.prototyped;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hayden.proto.prototype.Prototype;

public interface Prototyped<P extends Prototype> {

    @JsonIgnore
    P proto();

}
