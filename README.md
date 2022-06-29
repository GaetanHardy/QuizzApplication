# QuizzApplication

## Lancement de l'application
 - Créer un répertoire pour accueillir le contenu du répertoire git
 - Cloner le répertoire git avec : git clone https://github.com/GaetanHardy/QuizzApplication.git
 - Lancer et mettre à jour Android Studio
 - Exécuter le projet et démarrer une machine Android virtuelle 


## L'accès à la base de données se fait automatiquement, il n'y a pas besoin de reconnecter à Firebase au moment de l'exécution

L'application s'ouvre sur l'activité de connexion. Voici les différents logins possibles : 

- Identifiant : Gaëtan / Mot de passe : gaetan
- Identifiant : Léo / Mot de passe : leo
- Identifiant : Sofia / Mot de passe : sofia
- Identifiant : Robin / Mot de passe : robin

Une fois la connexion établie, l'application renvoie sur l'activité principale contenant les amis de l'utilisateur ainsi que leurs scores.
Le clic sur le bouton "Commencer la partie" démarrera le quizz comportant 5 questions, valant 20 points chacune.

Une fois le quizz terminé, le score du joueur est mis à jour et le joueur est renvoyé à l'écran principal.
