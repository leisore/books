package cn.leisore.coder.common;

import junit.framework.TestCase;

public class ReflectionsTest extends TestCase {

    public void testGetFieldVal() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

        A a = new A();
        a.name = "leisore";
        a.b = new B();
        a.b.c = new C();
        a.b.c.id = 10;

        assertEquals(Reflections.getFieldVal(a, "name"), "leisore");
        assertEquals(Reflections.getFieldVal(a, "b"), a.b);
        assertEquals(Reflections.getFieldVal(a, "b", "c"), a.b.c);
        assertEquals(Reflections.getFieldVal(a, "b", "c", "id"), 10);

        try {
            Reflections.getFieldVal(a);
            assertTrue(false);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }

        try {
            Reflections.getFieldVal(a, "c");
            assertTrue(false);
        } catch (NoSuchFieldException e) {
            assertTrue(true);
        }

        try {
            Reflections.getFieldVal(a, "b", "c", "d");
            assertTrue(false);
        } catch (NoSuchFieldException e) {
            assertTrue(true);
        }

        A a1 = new A();
        assertNull(Reflections.getFieldVal(a1, "b"));
    }

    static class C {
        public int id;
    }

    static class B {
        private C c;
    }

    static class A {
        B b;
        String name;
    }
}
