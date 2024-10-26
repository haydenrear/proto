package org.springframework.core.annotation;

import com.hayden.proto.prototyped.sources.server.requestresponse.ServerEndpoint;
import lombok.SneakyThrows;
import org.reflections.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class ExposeAnnotationTypeMapping {

    /**
     * @param ty annotation with aliases
     * @return a map with the key being the original annotation being aliased and the value being the aliaser - where the aliaser will also
     *         be annotated by the aliased as an annotation on the type.
     * @throws NoSuchMethodException
     */
    public static Map<Method, List<Method>> resolveAliasedForTargets(Class<? extends Annotation> ty) throws NoSuchMethodException {
        var annotationTypeMapping = new AnnotationTypeMapping(null, ty, null, new HashSet<>());
        Method resolveAliasedForTargets = annotationTypeMapping.getClass().getDeclaredMethod("resolveAliasedForTargets");
        resolveAliasedForTargets.trySetAccessible();
        return (Map<Method, List<Method>>) ReflectionUtils.invoke(resolveAliasedForTargets, annotationTypeMapping);
    }


//    @SneakyThrows
//    public static void main(String[] args) {
//        var t = resolveAliasedForTargets(ServerEndpoint.class);
//        System.out.printf("t");
//    }

}
