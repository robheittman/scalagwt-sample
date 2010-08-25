package com.google.gwt.sample.showcase.client

/*
 * Copyright 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

import com.google.gwt.event.dom.client.ChangeEvent
import com.google.gwt.event.dom.client.ChangeHandler
import com.google.gwt.event.dom.client.ClickEvent
import com.google.gwt.event.dom.client.ClickHandler
import com.google.gwt.event.logical.shared.SelectionEvent
import com.google.gwt.event.logical.shared.SelectionHandler
import com.google.gwt.event.logical.shared.ValueChangeEvent
import com.google.gwt.event.logical.shared.ValueChangeHandler


/**
 * Provides implicit conversions that allow functions to be substituted where handlers are called for.
 */
object Handlers {
   implicit def fn2changeHandler(fn: ChangeEvent => Unit): ChangeHandler =
      new ChangeHandler() {
         def onChange(event: ChangeEvent) = fn(event)
      }
   implicit def fn2clickHandler(fn: ClickEvent => Unit): ClickHandler =
      new ClickHandler() {
         def onClick(event: ClickEvent) = fn(event)
      }
   implicit def fn2selectionHandler[T](fn: SelectionEvent[T] => Unit): SelectionHandler[T] =
      new SelectionHandler[T] {
         def onSelection(event: SelectionEvent[T]): Unit = fn(event)
      }
   implicit def fn2valueChangeHandler[T](fn: ValueChangeEvent[T] => Unit): ValueChangeHandler[T] =
      new ValueChangeHandler[T] {
         def onValueChange(event: ValueChangeEvent[T]): Unit = fn(event)
      }
}


