/*
 * @(#)FirstNonNullResultExitStrategy.java 2012-8-1 下午10:00:00
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.strategy.exit.impl;

import org.makersoft.shards.Shard;
import org.makersoft.shards.strategy.exit.ExitOperationsCollector;
import org.makersoft.shards.strategy.exit.ExitStrategy;
import org.makersoft.shards.utils.Assert;

/**
 * 
 */
public class FirstNonNullResultExitStrategy<T> implements ExitStrategy<T> {

	private T nonNullResult;
	private Shard shard;

	/**
	 * Synchronized method guarantees that only the first thread to add a result
	 * will have its result reflected.
	 */
	public final synchronized boolean addResult(T result, Shard shard) {
		Assert.notNull(shard);
		if (result != null && nonNullResult == null) {
			nonNullResult = result;
			this.shard = shard;
			return true;
		}
		return false;
	}

	public T compileResults(ExitOperationsCollector exitOperationsCollector) {
		return nonNullResult;
	}

	public Shard getShardOfResult() {
		return shard;
	}
}
