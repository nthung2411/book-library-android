package com.aavn.devday.booklibrary.data.model;

public class ResponseData<T> {
    public enum State {
        LOADING,
        SUCCESS,
        ERROR
    }

    private State state;
    private T data;
    private String message = "";

    public ResponseData(State state, T data, String message) {
        this.state = state;
        this.data = data;
        this.message = message;
    }

    public ResponseData(State state, T data) {
        this.state = state;
        this.data = data;
    }

    public ResponseData(State state, String message) {
        this.state = state;
        this.message = message;
    }

    public static ResponseData loading(){
        return new ResponseData(State.LOADING);
    }

    public static ResponseData error(String message){
        return new ResponseData(State.ERROR, message);
    }

    public static <T> ResponseData<T> success(T data){
        return new ResponseData(State.SUCCESS, data);
    }

    public ResponseData(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
