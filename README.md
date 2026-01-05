Pastoralist Resource Stress & Conflict Early-Warning System
Project Overview

This project develops a data-driven early-warning system for detecting pasture stress and potential conflict hotspots in arid and semi-arid pastoralist regions of Kenya, with a focus on Wajir County (and extendable to Turkana, Marsabit, Samburu, and West Pokot).

The system integrates satellite-derived environmental indicators with simulated mobility (CDR) anomaly detection to flag emerging risk zones before crises escalate. Results are visualized through an interactive Streamlit dashboard to support early interventions such as water trucking, fodder deployment, livestock offtake, and peace actions.

Objectives

Detect vegetation and rainfall stress using NDVI and CHIRPS data

Forecast future pasture stress hotspots at 5 km spatial resolution

Identify abnormal livestock/human movement patterns using anomaly detection

Provide explainable AI outputs (SHAP) to build trust with stakeholders

Support early conflict prevention and climate resilience planning

 Data Sources
Environmental Data

NDVI (MODIS) – vegetation greenness

CHIRPS rainfall – 16-day accumulated precipitation

5 km spatial grid points – uniform spatial analysis

Mobility Data (Simulated)

Anonymized Call Detail Record (CDR)–like mobility data

Used to represent livestock and pastoralist movement patterns

Aggregated at grid-cell level (no personal data)

No personal or sensitive data is used in this project.

Methodology
1️ Environmental Stress Modeling

NDVI anomalies computed using seasonal climatology

Rainfall deficits extracted per grid cell

Lagged features added for temporal learning

Machine learning model (Logistic Regression / XGBoost baseline)

Output: Probability of future pasture stress hotspot

2️ Anomaly Detection (Recommendation #2)

Simulated CDR mobility features:

Movement intensity

Directional changes

Spatial congregation

Isolation Forest used to detect abnormal movement patterns

High anomaly scores indicate early distress, migration pressure, or pre-conflict signals

3️ Explainable AI (Recommendation #3)

SHAP (SHapley Additive exPlanations) applied to trained models

Identifies which variables (NDVI, rainfall, mobility) drive hotspot predictions

Outputs:

Global feature importance

Local explanations for high-risk cells

4️ Decision Support & Early Action (Recommendation #4)

Model outputs can trigger:

Livestock offtake programs

Water trucking

Fodder distribution

Field verification and peacebuilding interventions

 Interactive Dashboard

Built using Streamlit + PyDeck. 
