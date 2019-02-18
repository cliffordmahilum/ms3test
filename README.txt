TITLE: Technical Test Challenge - Clifford Gonzales Mahilum

Program Technical Details (Development):

	*****************************************************************
	Operating System		: 	Windows 10 64bit
	*****************************************************************
	Programming/Tool/Framework	:	JAVA, Maven, Swing
	*****************************************************************
	IDE				:	Apache NetBeans IDE 10.0
	*****************************************************************
	Database			:	SQLite
	*****************************************************************
	Dependencies
				groupId		artifactId	version
				com.opencsv	opencsv		4.1
				org.xerial	sqlite-jdbc	3.23.1
	*****************************************************************

Additional Notes:
	Main Class Location: (ms3test)/src/main/java/com/ms3test/csvimport/mahilum/App.java
	* use this class as main or start up when debugging project
	* after running the application, there must be a UI for selecting CSV file.
	* On UI form, click 'Browse' then wait until processing is done.
	* Logs will be displayed through a text area below browse button (and with current selected directory)
	* Database will be automatically created locally e.i. "(ms3test)/src/(test.db)"
	* bad-data-<timestamp>.csv file will be saved in the sae directory with (test.db)