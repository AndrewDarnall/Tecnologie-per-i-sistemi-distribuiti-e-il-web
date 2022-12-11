<!DOCTYPE html>
<html>
	<head>
		<title> PHP Welcome page </title>
	</head>
	<body>
		<div id="main">
			<?php 
					
				echo "Greetings from the php module, ciao Giovanni!<br>";

				$addr = "localhost";
				$user = "gb";
				$password = "gb";
				$dbName = "Libri";

				$conn = new mysqli($addr,$user,$password,$dbName);

				if($conn->connect_error) {
					die("Database connection error!" . connect_error);
				} 

				echo "Successfully connected to the  MySQL database with addres: " . $addr . "\tand username:\t" . $user;

				// Preparing to execute a query
				$sql = "select * from libri";
				
				// Equivalent to the Java result set
				$results = $conn->query($sql);

				echo "<br><br>";

				echo "<h1 style=\"text-align:center;\"> Our collection of Books </h1><br><br>";

				if($results->num_rows > 0) {
					while($row = $results->fetch_assoc()) {
						echo "id:\t" . $row['id'] . "\tname:\t" . $row['name'] . "<br>";
					}
				} else {
					echo "<p>There are currently no books in the collection</p><br>";
				}

				$conn->close();

			?>
		</div>
	</body>
</html>
