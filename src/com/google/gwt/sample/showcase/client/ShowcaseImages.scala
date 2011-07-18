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
package com.google.gwt.sample.showcase.client

import com.google.gwt.resources.client.ClientBundle
import com.google.gwt.resources.client.ImageResource

/**
 * The images used throughout the Showcase.
 */
trait ShowcaseImages extends ClientBundle {

  def catI18N(): ImageResource

  def catLists(): ImageResource

  def catOther(): ImageResource

  def catPanels(): ImageResource

  def catPopups(): ImageResource

  def catTables(): ImageResource

  def catTextInput(): ImageResource

  def catWidgets(): ImageResource

  def gwtLogo(): ImageResource

  def gwtLogoThumb(): ImageResource

  def jimmy(): ImageResource

  def jimmyThumb(): ImageResource

  /**
   * Indicates the locale selection box.
   */
  def locale(): ImageResource
}
