package com.hayden.proto.prototyped.metadata.model;

import com.hayden.proto.prototype.Prototype;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PrototypeModel {

    String id;

    Prototype prototype;

}
