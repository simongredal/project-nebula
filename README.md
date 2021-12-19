VIGTIGT LINK TIL HEROKU [NEBULA PROJECT MANAGER](https://nebula-project-manager.herokuapp.com/)

<div align="center">
  <a href="https://github.com/simongredal/project-nebula/">
    <img src="src/main/resources/static/images/starry-round-logo.svg" alt="Logo" width="100" height="100">
  </a>

  <h2 align="center">Nebula Project Manager</h2>
  <div align="center">
  
[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]
  
  </div>
  <p align="center">
    2. Semesters eksamensprojekt
    <br />
    <a href="https://github.com/othneildrew/Best-README-Template"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://github.com/othneildrew/Best-README-Template">View Demo</a>
    ·
    <a href="https://github.com/othneildrew/Best-README-Template/issues">Report Bug</a>
    ·
    <a href="https://github.com/othneildrew/Best-README-Template/issues">Request Feature</a>
  </p>
  
  ![Product Screenshot][product-screenshot]
</div>


## Built With

* [Java 17](https://jdk.java.net/17/)
* [MySQL 8](https://dev.mysql.com/downloads/mysql/)
* [Maven](https://maven.apache.org)
* [Spring Boot](https://spring.io/projects/spring-boot)
* [Bouncy Castle](https://www.bouncycastle.org/java.html)
* [SQL2O](https://www.sql2o.org)
* [Gantt.js](https://webdesign.tutsplus.com/tutorials/build-a-simple-gantt-chart-with-css-and-javascript--cms-33813)


## Getting Started

Below are two guides on how to get started with this repository.
One for deploying Nebula to Heroku and one for hacking on Nebula locally.

### Deploy to Heroku

#### Prerequisites
First you need a Heroku account with payment information attached to enable add-ons, even if you only use their free-tier.  
Second you need to [install git](https://git-scm.com/downloads) and you need to [install Heroku's CLI tool](https://devcenter.heroku.com/articles/heroku-cli).  

#### Guide 
1. Clone this git repository to your computer.  
   ```sh
   git clone "https://github.com/simongredal/project-nebula"
   ```

2. Go to the cloned git repository.
   ```sh
   cd project-nebula/
   ```

3. Activate the Heroku CLI tool by running the following command in a terminal and following the instructions.
   ```sh
   heroku login
   ```

4. Create a new Heroku App, if you don't specify a name Heroku will generate one for you.
   Every App on Heroku must have a unique name.
   ```sh
   heroku apps:create YOUR_UNIQUE_APP_NAME_HERE
   ```

5. Create an add-on for your app.
   We are using the [JawsDB add-on](https://elements.heroku.com/addons/jawsdb) because it provides MySQL 8.
   We are also specifying to use the kitefin tier because it is free.
   ```sh
   heroku addons:create jawsdb:kitefin --version=8.0
   ```

6. Show all the current environment variables.
   The JawsDB add-on should have created a config variable for us, but we need to copy it so it can get the name that our code expects.
   ```sh
   heroku config
   ```
   
7. Copy the `mysql://...` part and save it as a new config variable.
   ```sh
   heroku config:set DB_URL=YOUR_MYSQL_URL_HERE
   ```

8. Set up your Heroku App as an extra remote for your cloned git repository
   ```sh
   heroku git:remote -a YOUR_UNIQUE_APP_NAME_HERE
   ```

9. Finally push the code from the your cloned git repository to your Heroku App.
   ```sh
   git push remote main
   ```


### Begin Developing Locally

1. Clone this git repository to your computer using the following command:  
   ```sh
   git clone "https://github.com/simongredal/project-nebula"
   ```
2. Open your editor, we are currently using IntelliJ. Make sure to check the _built with_ section, to check if your editor support these languages. 

3. You are now ready to start developing locally on your own computer. 




<!-- USAGE EXAMPLES -->
## Usage

Use this space to show useful examples of how a project can be used. Additional screenshots, code examples and demos work well in this space. You may also link to more resources.



<!-- ROADMAP -->
## Roadmap

- [x] Add Changelog
- [x] Add back to top links
- [ ] Add Additional Templates w/ Examples
- [ ] Add "components" document to easily copy & paste sections of the readme
- [ ] Multi-language Support
    - [ ] Chinese
    - [ ] Spanish

See the [open issues](https://github.com/othneildrew/Best-README-Template/issues) for a full list of proposed features (and known issues).

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE.txt` for more information.

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- CONTACT -->
## Contact

Your Name - [@your_twitter](https://twitter.com/your_username) - email@example.com

Project Link: [https://github.com/your_username/repo_name](https://github.com/your_username/repo_name)

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- ACKNOWLEDGMENTS -->
## Acknowledgments

Use this space to list resources you find helpful and would like to give credit to. I've included a few of my favorites to kick things off!

* [Choose an Open Source License](https://choosealicense.com)
* [GitHub Emoji Cheat Sheet](https://www.webpagefx.com/tools/emoji-cheat-sheet)
* [Malven's Flexbox Cheatsheet](https://flexbox.malven.co/)
* [Malven's Grid Cheatsheet](https://grid.malven.co/)
* [Img Shields](https://shields.io)
* [GitHub Pages](https://pages.github.com)
* [Font Awesome](https://fontawesome.com)
* [React Icons](https://react-icons.github.io/react-icons/search)

<p align="right">(<a href="#top">back to top</a>)</p>



[contributors-shield]: https://img.shields.io/github/contributors/simongredal/project-nebula.svg?style=for-the-badge
[contributors-url]: https://github.com/simongredal/project-nebula/graphs/contributors

[forks-shield]: https://img.shields.io/github/forks/simongredal/project-nebula.svg?style=for-the-badge
[forks-url]: https://github.com/simongredal/project-nebula/network/members

[stars-shield]: https://img.shields.io/github/stars/simongredal/project-nebula.svg?style=for-the-badge
[stars-url]: https://github.com/simongredal/project-nebula/stargazers

[issues-shield]: https://img.shields.io/github/issues/simongredal/project-nebula.svg?style=for-the-badge
[issues-url]: https://github.com/simongredal/project-nebula/issues

[license-shield]: https://img.shields.io/github/license/simongredal/project-nebula.svg?style=for-the-badge
[license-url]: https://github.com/simongredal/project-nebula/blob/master/LICENSE.txt

[product-screenshot]: images/screenshot.png
