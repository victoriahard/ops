/*
 * WatchesMainPanel.java
 *
 * Created on den 2 juni 2008, 21:52
 */

package watches;

import java.awt.GridLayout;
import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Vector;
import javax.swing.border.TitledBorder;
import opsdebugger2.tabpanels.plotter.ValueTransferHandler;

/**
 *
 * @author  angr
 */
public class WatchesMainPanel extends javax.swing.JPanel implements PropertyChangeListener 
{
    int ix = 0;
    int iy = 0;
    private Vector<ValueWatch> valueWatches = new Vector<ValueWatch>();
    /** Creates new form WatchesMainPanel */
    public WatchesMainPanel() 
    {
        initComponents();
        jList1.setCellRenderer(new ValueWatchListRenderer());
        jList1.setListData(valueWatches);
        
        ValueTransferHandler vTransHand = new ValueTransferHandler();
        vTransHand.addPropertyChangeListener(new PropertyChangeListener()
        {

            public void propertyChange(PropertyChangeEvent evt)
            {
                String valeuName = (String) evt.getNewValue();
                
                ValueWatch vw = new ValueWatch();
                vw.setValueOfInterest(valeuName);
                addNewValueWatch(vw);

            }

            
        });

        
        setTransferHandler(vTransHand);
        
    }
    private void addNewValueWatch(ValueWatch vw)
    {
       
//        add(vw);
//        vw.setVisible(true);
//        updateUI();
//        iy++;
        vw.addPropertyChangeListener(this);
        valueWatches.add(vw);
        //jList1.updateUI();
        
        ((TitledBorder)getBorder()).setTitle("");
        updateUI();
        jList1.updateUI();
        
        
        
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jButton1 = new javax.swing.JButton();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(opsdebugger2.OPSDebugger2App.class).getContext().getResourceMap(WatchesMainPanel.class);
        setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("Form.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 11), resourceMap.getColor("Form.border.titleColor"))); // NOI18N
        setName("Form"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jList1.setName("jList1"); // NOI18N
        jScrollPane1.setViewportView(jList1);

        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jButton1)
                .addContainerGap())
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 605, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 487, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1))
        );
    }// </editor-fold>//GEN-END:initComponents

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
// TODO add your handling code here:
    valueWatches.remove(jList1.getSelectedValue());
    jList1.updateUI();
}//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    public void propertyChange(PropertyChangeEvent evt)
    {
        updateUI();
    }

}
