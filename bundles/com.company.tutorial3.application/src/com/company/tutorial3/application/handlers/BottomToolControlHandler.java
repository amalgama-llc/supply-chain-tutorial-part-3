package com.company.tutorial3.application.handlers;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.menu.MToolControl;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import com.company.tutorial3.application.AppInfo;
import com.company.tutorial3.application.utils.Topics;
import com.company.tutorial3.common.localization.Messages;

public class BottomToolControlHandler {
	@Inject
	private IEventBroker eventBroker;
	
	@Inject
	private EModelService modelService;
	
	@Inject
	@Translation
	private Messages messages;
	
	private MToolControl mToolControl;
	
	@PostConstruct
	public void createControls(Composite parent, MToolControl mToolControl, IEclipseContext context) {
		parent.setLayout(new FormLayout());
		this.mToolControl = mToolControl;
		Composite childComposite = new Composite(parent, SWT.NONE);
		childComposite.setLayout(new FormLayout());
		setFormData(childComposite, 0, 0, 100, 0);
		
		Label labelPerspective = new Label(childComposite, SWT.NONE);
		labelPerspective.setText(getActivePerspective().getLocalizedLabel());
		setFormData(labelPerspective, 0, 5, 20, -5);
		
		Label labelCoordinates = new Label(childComposite, SWT.NONE);
		labelCoordinates.setText(" ");
		setFormData(labelCoordinates, 20, 5, 30, -5);
		
		Label labelLicense = new Label(childComposite, SWT.NONE);
		labelLicense.setText("");
		setFormData(labelLicense, 30, 5, 65, -5);
		
		Label labelVersions = new Label(childComposite, SWT.NONE);
		labelVersions.setText(messages.APP_VERSION + ": " + AppInfo.getVersionAsString());
		labelVersions.setAlignment(SWT.RIGHT);
		setFormData(labelVersions, 65, 5, 75, -5);
		
		Label labelMemory = new Label(childComposite, SWT.NONE);
		labelMemory.setText("Memory usage: 0 of 0");
		labelMemory.setAlignment(SWT.RIGHT);
		setFormData(labelMemory, 75, 5, 100, -5);
		labelMemory.pack();
		labelMemory.addMouseListener(MouseListener.mouseDownAdapter(e -> {
			System.gc();
			updateMemoryUsageLabel(labelMemory);
		}));
		
		eventBroker.subscribe(Topics.PERSPECTIVE_CHANGED, event -> {
			if (labelPerspective != null && !labelPerspective.isDisposed()) {
				labelPerspective.setText(String.valueOf(getActivePerspective().getLocalizedLabel()));
				labelPerspective.pack();
			}
		});
		
		childComposite.addPaintListener(event -> {
			if (childComposite != null && !childComposite.isDisposed()) {
				childComposite.requestLayout();
			}
			if (labelCoordinates != null && !labelCoordinates.isDisposed()) {
				labelCoordinates.pack();
			}
			if (labelPerspective != null && !labelPerspective.isDisposed()) {
				labelPerspective.pack();
			}
			if (labelMemory != null && !labelMemory.isDisposed()) {
				labelMemory.pack();
			}
		});
		
		updateMemoryUsage(labelMemory);
		updatePerspectiveLabel(labelPerspective);
	}
	
	private String getLicence() {
		return "";
	}
	
	private void updateMemoryUsage(Label label) {
		updateMemoryUsageLabel(label);
		Display.getDefault().timerExec(5000, () -> updateMemoryUsage(label));
	}
	
	private void updatePerspectiveLabel(Label label) {
		if (label != null && !label.isDisposed()) {
			label.setText(getActivePerspective().getLocalizedLabel());
			label.pack();
		}
		Display.getDefault().timerExec(2000, () -> updatePerspectiveLabel(label));
	}
	
	private void updateMemoryUsageLabel(Label label) {
	    Runtime rt = Runtime.getRuntime();
	    long maxMemory = rt.maxMemory() / 1024 / 1024;
	    long freeMemory = rt.freeMemory() / 1024 / 1024;
	    long totalMemory = rt.totalMemory() / 1024 / 1024;
	    if (label != null && !label.isDisposed()) {
	    	label.setText(messages.label_memory_usage + ": " + (totalMemory - freeMemory) + " " + messages.label_of + " " + maxMemory + " " + messages.label_mb);
	    }
	}
	
	private void setFormData(Control control, int leftRelativeOffset, int leftRelativeOffsetGap, int rightRelativeOffset, int rightRelativeOffsetGap) {
		FormData formData = new FormData();
		formData.left = new FormAttachment(leftRelativeOffset, leftRelativeOffsetGap);
		formData.right = new FormAttachment(rightRelativeOffset, rightRelativeOffsetGap);
		control.setLayoutData(formData);
	}
	
	private MPerspective getActivePerspective() {
		return modelService.getActivePerspective(modelService.getTopLevelWindowFor(mToolControl));
	}

}



