package com.amigos.awsuploadimage.service;

public interface WSService {
    void notifyFrontEnd(final String message);
    void notifyUser(final Long id, final String message);
}
