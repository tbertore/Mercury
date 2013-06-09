package org.mercury;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.mercury.gfx.Camera;

public class InputHandler implements KeyListener, MouseListener {
   
   private boolean[] keys = new boolean[65536];
   public boolean up, down, left, right, attack;
   public Point clickCanvas = new Point();
   public Point clickScreen = new Point();
   public Point clickWorld = new Point();
   public boolean clickAbsorbed = true;
   public boolean mouseDown = false;
   private Camera camera;
   
   public InputHandler(Camera camera) {
	   this.camera = camera;
   }
   
   public void tick() {
      up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
      down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
      left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
      right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
      attack = keys[KeyEvent.VK_SPACE];
   }
   
   @Override
   public void keyPressed(KeyEvent e) {
      keys[e.getKeyCode()] = true;
   }

   @Override
   public void keyReleased(KeyEvent e) {
      keys[e.getKeyCode()] = false;
   }

   @Override
   public void keyTyped(KeyEvent e) {
   }

   @Override
   public void mouseClicked(MouseEvent me) {
      // TODO Auto-generated method stub
      
   }

   @Override
   public void mouseEntered(MouseEvent me) {
      // TODO Auto-generated method stub
      
   }

   @Override
   public void mouseExited(MouseEvent me) {
      // TODO Auto-generated method stub
      
   }

   @Override
   public void mousePressed(MouseEvent me) {
      // TODO Auto-generated method stub
      clickCanvas = me.getPoint();
      clickScreen.x = clickCanvas.x; // Add support for pixel scaling.
      clickScreen.y = clickCanvas.y;
      clickWorld.x = clickScreen.x + camera.x;
      clickWorld.y = clickScreen.y + camera.y;
      clickAbsorbed = false;
      mouseDown = true;
   }

   @Override
   public void mouseReleased(MouseEvent me) {
      mouseDown = false;
      
   }

}
