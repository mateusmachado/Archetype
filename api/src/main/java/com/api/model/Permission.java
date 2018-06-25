package com.api.model;

public enum Permission {
    read, // index, show, export(csv, excel)
    create, // new, create, import(csv, excel)
    update, // edit, update
    destroy // destroy
}
