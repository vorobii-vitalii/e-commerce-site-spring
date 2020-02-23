package com.commerce.web.mappers;

import com.commerce.web.exceptions.NotImplementedException;

public abstract class Mapper<A, B> {

    public abstract A from(B b) throws NotImplementedException;

    public abstract B to(A a) throws NotImplementedException;

}
