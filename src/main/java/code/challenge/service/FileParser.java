package code.challenge.service;

import java.io.IOException;

public interface FileParser<T, R> {
    R parseFile(T t) throws IOException;
}
