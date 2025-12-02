#!/bin/bash

# Run the Swing GUI application
# Make sure MySQL is running and database is set up

cd "$(dirname "$0")"

# Compile all Java files
echo "Compiling Java files..."
javac -cp "app/lib/mysql-connector-j-9.5.0.jar" -d app/classes app/src/**/*.java 2>&1 | head -20

# Run the Swing app
echo "Starting GUI..."
java -cp "app/lib/mysql-connector-j-9.5.0.jar:app/classes" ui.swing.SwingApp

