#!/bin/sh
## make datasets folder, which points to raw data
mkdir datasets
## make spark-warehouse folder, this is where the resulting parquets from preprocessing are stored
mkdir spark-warehouse
## Link datasets
ln --symbolic $PRESCRIPTION_PATH/hospitalization datasets/hospitalization
ln --symbolic $PRESCRIPTION_PATH/prescription datasets/prescription
ln --symbolic $PRESCRIPTION_PATH/npr datasets/npr
ln --symbolic $PRESCRIPTION_PATH/elders_drug_duration datasets/elders_drug_duration
ln --symbolic $PRESCRIPTION_PATH/atc_to_descr datasets/atc_to_descr