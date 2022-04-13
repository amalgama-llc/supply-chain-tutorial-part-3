# Step 3: Adding User-friendly Interface

## About
This repository contains the source code for the [Step 3 in the Amalgama Platform tutorial](https://platform.amalgamasimulation.com/amalgama/platform_tutorial_step_3.html).

The desktop application simulates the functionality of a simple supply chain.
A road network can be edited and displayed over a map.
In Simulation mode, trucks, warehouses, and stores are displayed.
Simulation statistics is also shown and updated in real time.

## How to build and run
1. Install JavaFX. Create a new environment variable 'JAVAFX_HOME' that contains the path to your local JavaFX installation.
1. Clone the repository to your local machine.
1. [Get access](https://platform.amalgamasimulation.com/amalgama/quick_start_access.html) to the Amalgama Platform libraries.
1. [Install and configure](https://platform.amalgamasimulation.com/amalgama/quick_start_eclipse.html) Eclipse IDE.
1. Start Eclipse with new workspace.
1. In Eclipse, [set up](https://platform.amalgamasimulation.com/amalgama/quick_start_desktop.html#_creating_an_application) JavaFX libraries location.
1. Open the project in Eclipse.
1. Open the 'releng/com.company.tutorial3.target/com.company.tutorial3.target' file and set it as active target platform. Wait till dependencies are resolved. 
When a username/password window appears to access the Amalgama P2 repository (its URL ends with 'platform-p2'), use your Amalgama credentials.
1. Open the 'releng/com.company.tutorial3.product/com.company.tutorial3.product' file In Eclipse.
Start the desktop application by pressing the 'Launch an Eclipse application' in the top right corner of the product file editor.

You can use a sample scenario from the 'scenario' folder.