package org.upsilongang.upsilon.command;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CommandParameters
{
    String description() default "";
    String usage() default "/<command>";
    String aliases() default "";
    CommandSource source() default CommandSource.ANY;
    boolean op() default false;
}