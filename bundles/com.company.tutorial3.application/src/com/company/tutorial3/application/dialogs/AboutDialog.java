package com.company.tutorial3.application.dialogs;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

import com.company.tutorial3.application.AppInfo;

public class AboutDialog extends TitleAreaDialog {
	
	private static final String ABOUT_COMPANY_TEXT =
			""" 
			%s
			%s
			
			%s
			Version: %s
			
			%s""";
	
	private AppInfo appInfo;
	
	public AboutDialog(Shell shell, AppInfo appInfo) {
		super(shell);
		this.appInfo = appInfo;
	}
	
	@Override
	public void create() {		
		super.create();		
		getShell().pack();
		getShell().setText("About");
		setTitle(appInfo.getFullProductName());
		setTitleImage(appInfo.getApplicationIcon());
		getButton(IDialogConstants.OK_ID).setFocus();
		
		// Show the window in the center of the current desktop
		Monitor primary = Display.getCurrent().getPrimaryMonitor();
		Rectangle bounds = primary.getBounds();
		Rectangle rect = getShell().getBounds();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		getShell().setLocation(x, y);
	}
	
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.CLOSE_LABEL, true);
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);		
		Composite comParent = new Composite(area, SWT.NONE);	
		comParent.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		GridLayout formLayout = new GridLayout(1, false);
		formLayout.marginWidth = 10;
		formLayout.marginHeight = 10;
		comParent.setLayout(formLayout);
		
		createLabel(comParent, 
				ABOUT_COMPANY_TEXT.formatted(	appInfo.getAppVendorName(),
												appInfo.getAppVendorSiteUrl(),
												appInfo.getProductID(), 
												appInfo.getVersionAsString(),
												appInfo.getAppVendorEmail()));
		
		return area;
	}
	
	private static Label createLabel(Composite parent, String text) {
		Label label = new Label(parent, SWT.NONE);
		label.setText(text);
		label.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
		var gridData = new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1);
		gridData.widthHint = 300;
		label.setLayoutData(gridData);
		return label;
	}
}

