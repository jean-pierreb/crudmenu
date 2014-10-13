package utils.tags;

import org.scalatest.TagAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Use this annotation to tag a class or method as a DbTest.
 *
 * <pre>
 *     &#064;DbTest
 *     class TestSpec {
 *         "integration test 1" { ... }
 *
 *         "integration test 2" { ... }
 *     }
 * </pre>
 *
 * You can then exclude or include tests based on the tag. Example with SBT console, exclude all tests with tag DbTest:
 *   <pre>test-only -- -l crudmenu.utils.tags.DbTest</pre>
 *
 */
@TagAnnotation
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface DbTest {}
