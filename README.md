# Part 3: Adding User-friendly Interface

## About
This repository contains the source code for the [Part 3 of the Supply Chain tutorial](https://platform.amalgamasimulation.com/amalgama/SupplyChainTutorial/sc_tutorial_part_3.html).

The desktop application simulates the functionality of a simple supply chain.
A road network can be edited and displayed over a map.
In Simulation mode, trucks, warehouses, and stores are displayed.
Simulation statistics is also shown and updated in real time.

## How to build and run using Eclipse
1. Download and install [Eclipse With Amalgama Platform](https://platform.amalgamasimulation.com/amalgama/quick_start_eclipse.html).
1. Start Eclipse with a new workspace.
1. Clone this repository to your local machine.
1. Import the project (with its nested projects) into Eclipse.
1. Open the `releng/com.company.tutorial3.product/com.company.tutorial3.product` file In Eclipse.
1. Start the desktop application by pressing the 'Launch an Eclipse application' button in the top right corner of the product file editor.

You can use a sample scenario from the 'scenario' folder.



## How to build a standalone Windows application with Maven
1. Install JDK-17 and Maven 3.8.6+.
1. Run `mvn clean package`.

After the building process is finished, the standalone Windows application will be located in the `./releng/com.company.tutorial3.product/target/products` folder:
- the ready-to-start app in the `tutorial3` subfolder;
- the ready-to-ship `tutorial3-1.0.0-win32.win32.x86_64.zip` file.
