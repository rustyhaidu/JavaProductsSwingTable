package demo;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import entities.Product;
import model.ProductModel;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class JFrameProduct {

	protected Shell shlProductManagement;
	private Table tableProduct;
	private Text textId;
	private Text textName;
	private Text textPrice;
	private Text textQuantity;
	private Text textDescription;
	private ProductModel pm = new ProductModel();

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			JFrameProduct window = new JFrameProduct();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void fillData() {
		tableProduct.removeAll();
		for (Product p : pm.findAll()) {
			TableItem tableItem = new TableItem(tableProduct, SWT.NONE);
			tableItem.setText(new String[] { String.valueOf(p.getId()), p.getName(), String.valueOf(p.getPrice()),
					String.valueOf(p.getQuantity()), p.getDescription() });
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		fillData();
		shlProductManagement.open();
		shlProductManagement.layout();
		while (!shlProductManagement.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}

	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlProductManagement = new Shell();
		shlProductManagement.setSize(678, 423);
		shlProductManagement.setText("Product Management");

		tableProduct = new Table(shlProductManagement, SWT.BORDER | SWT.FULL_SELECTION);
		tableProduct.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TableItem[] selection = tableProduct.getSelection();
				int id = Integer.parseInt(selection[0].getText());
				Product p = pm.find(id);
				textDescription.setText(p.getDescription());
				textId.setText(String.valueOf(p.getId()));
				textName.setText(p.getName());
				textPrice.setText(String.valueOf(p.getPrice()));
				textQuantity.setText(String.valueOf(p.getQuantity()));
			}
		});
		tableProduct.setBounds(29, 10, 589, 141);
		tableProduct.setHeaderVisible(true);
		tableProduct.setLinesVisible(true);

		TableColumn tblclmnNewColumn = new TableColumn(tableProduct, SWT.NONE);
		tblclmnNewColumn.setWidth(100);
		tblclmnNewColumn.setText("Id");

		TableColumn tblclmnNewColumn_1 = new TableColumn(tableProduct, SWT.NONE);
		tblclmnNewColumn_1.setWidth(100);
		tblclmnNewColumn_1.setText("Name");

		TableColumn tblclmnNewColumn_2 = new TableColumn(tableProduct, SWT.NONE);
		tblclmnNewColumn_2.setWidth(100);
		tblclmnNewColumn_2.setText("Price");

		TableColumn tblclmnNewColumn_3 = new TableColumn(tableProduct, SWT.NONE);
		tblclmnNewColumn_3.setWidth(100);
		tblclmnNewColumn_3.setText("Quantity");

		TableColumn tblclmnNewColumn_4 = new TableColumn(tableProduct, SWT.NONE);
		tblclmnNewColumn_4.setWidth(184);
		tblclmnNewColumn_4.setText("Description");

		Label lblId = new Label(shlProductManagement, SWT.NONE);
		lblId.setBounds(29, 198, 55, 15);
		lblId.setText("Id");

		textId = new Text(shlProductManagement, SWT.BORDER);
		textId.setBounds(169, 198, 153, 21);

		Label lblName = new Label(shlProductManagement, SWT.NONE);
		lblName.setText("Name");
		lblName.setBounds(29, 219, 55, 15);

		textName = new Text(shlProductManagement, SWT.BORDER);
		textName.setBounds(169, 219, 153, 21);

		Label lblPrice = new Label(shlProductManagement, SWT.NONE);
		lblPrice.setText("Price");
		lblPrice.setBounds(29, 240, 55, 15);

		textPrice = new Text(shlProductManagement, SWT.BORDER);
		textPrice.setBounds(169, 240, 153, 21);

		Label lblQuantity = new Label(shlProductManagement, SWT.NONE);
		lblQuantity.setText("Quantity");
		lblQuantity.setBounds(29, 261, 55, 15);

		textQuantity = new Text(shlProductManagement, SWT.BORDER);
		textQuantity.setBounds(169, 261, 153, 21);

		Button btnNewButton = new Button(shlProductManagement, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Product p = new Product();
				p.setDescription(textDescription.getText());
				p.setName(textName.getText());
				p.setPrice(Double.parseDouble(textPrice.getText()));
				p.setQuantity(Integer.parseInt(textQuantity.getText()));
				if (pm.create(p)) {
					JOptionPane.showMessageDialog(null, "Add new product successfull");
					fillData();
				} else {
					JOptionPane.showMessageDialog(null, "Add new product unsuccessfull");
				}

			}
		});
		btnNewButton.setBounds(29, 323, 75, 25);
		btnNewButton.setText("Insert");

		Button btnUpdate = new Button(shlProductManagement, SWT.NONE);
		btnUpdate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Product p = new Product();
				p.setId(Integer.parseInt(textId.getText()));
				p.setDescription(textDescription.getText());
				p.setName(textName.getText());
				p.setPrice(Double.parseDouble(textPrice.getText()));
				p.setQuantity(Integer.parseInt(textQuantity.getText()));
				if (pm.edit(p)) {
					JOptionPane.showMessageDialog(null, "Edit product successfull");
					fillData();
				} else {
					JOptionPane.showMessageDialog(null, "Edit product unsuccessfull");
				}
			}
		});
		btnUpdate.setText("Update");
		btnUpdate.setBounds(145, 323, 75, 25);

		Button btnDelete = new Button(shlProductManagement, SWT.NONE);
		btnDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					TableItem[] selection = tableProduct.getSelection();
					int id = Integer.parseInt(selection[0].getText());
					Product p = pm.find(id);
					pm.delete(p);
					fillData();
				}
			}

		});
		btnDelete.setText("Delete");
		btnDelete.setBounds(260, 323, 75, 25);

		Label lblDescription = new Label(shlProductManagement, SWT.NONE);
		lblDescription.setText("Description");
		lblDescription.setBounds(29, 282, 103, 15);

		textDescription = new Text(shlProductManagement, SWT.BORDER);
		textDescription.setBounds(169, 282, 153, 21);

	}
}
