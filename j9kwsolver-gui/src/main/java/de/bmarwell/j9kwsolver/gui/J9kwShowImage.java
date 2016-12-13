package de.bmarwell.j9kwsolver.gui;

import de.bmarwell.j9kwsolver.api.domain.Captcha;

import com.google.common.base.Preconditions;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class J9kwShowImage {
  public String show(final Captcha captcha) {
    Preconditions.checkNotNull(captcha, "captcha in show().");
    String solution = null;

    if (!captcha.image().isPresent()) {
      return "";
    }

    ImageIcon icon = new ImageIcon(captcha.image().get());
    JLabel iconLabel = new JLabel(icon);

    solution = JOptionPane.showInputDialog(iconLabel);

    return solution;
  }
}
