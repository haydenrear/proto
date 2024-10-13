package com.hayden.proto.prototype.datasource.exec;

import com.hayden.proto.prototype.cardinality.All;
import com.hayden.proto.prototype.value.ClassValueContractProto;
import com.hayden.proto.prototype.Permitting;
import com.hayden.proto.prototyped.Value;
import com.hayden.proto.prototype.CompositePrototype;
import com.hayden.proto.prototype.Prototype;

public interface ExecContractProto extends CompositePrototype<ExecContractProto.ExecutionDescriptor> {

    sealed interface ExecutionDescriptor extends Prototype
        permits
            ExecutionDescriptor.InputType,
            ExecutionDescriptor.OutputType,
            ExecutionDescriptor.MethodName {

        record InputType<T>(ClassValueContractProto permitting, Class<T> value)
                implements ExecutionDescriptor, Permitting<ClassValueContractProto>, Value<Class<T>, InputType> {}

        record OutputType<T>(ClassValueContractProto permitting, Class<T> value)
                implements ExecutionDescriptor, Permitting<ClassValueContractProto>, Value<Class<T>, InputType> {}

        record MethodName(ClassValueContractProto permitting, String value)
                implements ExecutionDescriptor, Permitting<ClassValueContractProto>, Value<String, InputType> {}

    }

    @Override
    All<ExecutionDescriptor> prototypes();

}
