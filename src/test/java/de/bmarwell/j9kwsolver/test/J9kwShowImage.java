/**
 * Copyright (c) 2013, Benjamin Marwell.  This file is
 * licensed under the Affero General Public License version 3 or later.  See
 * the COPYRIGHT file.
 */
package de.bmarwell.j9kwsolver.test;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import de.bmarwell.j9kwsolver.domain.Captcha;

/**
 * @author Benjamin Marwell
 *
 */
public class J9kwShowImage {

	public String show(final Captcha captcha) {
		String solution = null;
		
		if (captcha == null) {
			return null;
		}
		
		if (captcha.getImage() == null) {
			return null;
		}
		
		ImageIcon icon = new ImageIcon(captcha.getImage());
		JLabel iconLabel = new JLabel(icon);
		
		solution = JOptionPane.showInputDialog(iconLabel);
		
		return solution;
	}
}
