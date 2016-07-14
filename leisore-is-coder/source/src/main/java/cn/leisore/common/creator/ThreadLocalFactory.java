package cn.leisore.common.creator;

public class ThreadLocalFactory<E> implements Factory<E> {

    private final ThreadLocal<E> cache = new ThreadLocal<E>() {
        @Override
        protected E initialValue() {
            return factory.create();
        };
    };

    private Factory<E> factory;

    public ThreadLocalFactory(Factory<E> factory) {
        this.factory = factory;
    }

    @Override
    public E create() {
        return cache.get();
    }
}
