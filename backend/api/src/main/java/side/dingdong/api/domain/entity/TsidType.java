package side.dingdong.api.domain.entity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.hibernate.annotations.Type;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Type(TsidUserType.class)
public @interface TsidType {
}
