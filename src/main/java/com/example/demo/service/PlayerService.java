package com.example.demo.service;

import com.example.demo.exceptions.WalletException;

import java.util.List;

public interface PlayerService<T> {
    T save(T t) throws WalletException;

    T update(T t, Long id) throws WalletException;

    List<T> getList();
}
