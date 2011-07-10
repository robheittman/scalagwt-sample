package com.google.gwt.sample.jribble.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

class Hello extends EntryPoint {

  def onModuleLoad() {
    val numbers = List(1, 2, 3, 4)
    val strings = numbers map (_.toString + "...")
    val s = strings mkString ", "
    val b = new Button("Click me", (_: ClickEvent) => Window.alert("Hello, AJAX, said Scala\nThose numbers are coming from list!\n" + s));
    RootPanel.get().add(b);
  }
  
  implicit def clickHandler(f: ClickEvent => Unit): ClickHandler = new ClickHandler {
    def onClick(event: ClickEvent) = f(event)
  }
}
