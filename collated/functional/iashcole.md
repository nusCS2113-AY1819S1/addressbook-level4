# iashcole
###### \java\seedu\address\ui\CheckPassword.java
``` java
        // ~start~
        pb.setText("owner");
        // ~end~
        pb.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (pb.getText().equals("password")) {
                    message.setText("Your password has been confirmed");
                    message.setTextFill(Color.web("black"));
                    AlertBox.display("Welcome", "Welcome, student!"
                            + " If you encounter any problems using the app, press F1 for help!");
                    isAdmin[0] = false;
                    role = new Role(Role.ROLE_STUDENT);
                    window.close();
                } else if (pb.getText().equals("owner")) {
                    message.setText("Your password has been confirmed");
                    message.setTextFill(Color.web("black"));
                    AlertBox.display("Welcome Owner", "Welcome back, you have administrative rights.");
                    isAdmin[0] = true;
                    role = new Role(Role.ROLE_ADMIN);
                    window.close();
                } else if (pb.getText().equals("accountant")) {
                    message.setText("Your password has been confirmed");
                    message.setTextFill(Color.web("black"));
                    AlertBox.display("Welcome Accountant", "Welcome back accountant, you are able to view statistics.");
                    role = new Role(Role.ROLE_ACCOUNTANT);
                    window.close();
                } else {
                    message.setText("Your password is incorrect!");
                    message.setTextFill(Color.web("red"));

                    //Maximum try of 3 counts
                    count++;
                    if (count == 3) {
                        AlertBox.display("Maximum Tries Exceeded", "Please try again later.");
                        System.exit(2);
                    }
                }
                pb.setText("");
            }
        });
        hb.getChildren().addAll(label, pb);
        vb.getChildren().addAll(hb, message);

        scene.setRoot(vb);
        window.showAndWait();
    }
}
```
