update pgmeas_d_classif_tree set classif_tree_editabile = TRUE where classif_id in (
select classif_id from pgmeas_d_classif_elem where classif_cod in ('QE_E21','QE_F3','QE_G3','QE_G6','QE_G9','QE_E19')
and data_cancellazione is null 
);

update pgmeas_d_classif_elem set classif_desc = 'f3) IVA su arredi e attrezzature' where classif_cod = 'QE_F3' and data_cancellazione is null;
update pgmeas_d_classif_elem set classif_desc = 'g3) IVA ribasso lavori' where classif_cod = 'QE_G3' and data_cancellazione is null;
update pgmeas_d_classif_elem set classif_desc = 'g6) IVA ribasso arredi e attrezzatura' where classif_cod = 'QE_G6' and data_cancellazione is null;
update pgmeas_d_classif_elem set classif_desc = 'g9) IVA Ribasso su progettazione (in caso di appalto integrato)' where classif_cod = 'QE_G9' and data_cancellazione is null;
update pgmeas_d_classif_elem set classif_desc = 'e19) IVA sui Lavori' where classif_cod = 'QE_E19' and data_cancellazione is null;