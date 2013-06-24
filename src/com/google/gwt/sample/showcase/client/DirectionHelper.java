package com.google.gwt.sample.showcase.client;

import com.google.gwt.i18n.client.HasDirection;

/** TODO this class exists as a temporary workaround for
 *  <a href="https://github.com/scalagwt/scalagwt-scala/issues/1">problems
 *  referring to enum values</a>
 */
public class DirectionHelper {
   public static HasDirection.Direction getLtrDirection() {
      return HasDirection.Direction.LTR;
   }
}
