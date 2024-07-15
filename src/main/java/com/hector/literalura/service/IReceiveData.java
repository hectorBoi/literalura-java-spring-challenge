package com.hector.literalura.service;

public interface IReceiveData {
    <T> T obtenerDatos(String json, Class<T> clase);
}
