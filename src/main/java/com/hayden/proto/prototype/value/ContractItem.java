package com.hayden.proto.prototype.value;

import com.hayden.proto.prototype.Prototype;

public interface ContractItem extends Prototype {

    record MaxLength() implements ContractItem {}

    record Nullable() implements ContractItem {}

}
