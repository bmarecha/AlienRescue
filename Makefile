SRCS	=	src/Launcher.java \
			src/Environnement.java \
			src/Ecran.java \
			src/Case.java \
			src/AffichageNiv.java \
			src/AffichageSelect.java \
			src/Plateau.java \
			src/Niveau.java

NAME = AlienRescue

bin/Launcher.class	: ${SRCS}
	javac ${SRCS}
	mv src/*.class* bin/

$(NAME)	: bin/Launcher.class
	java -Dfile.encoding=UTF-8 -classpath ./bin Launcher

all		:	${NAME}

clean	:
	rm -f bin/*.class* 

fclean	:	clean
	rm -f ${NAME}

re		: fclean all 

.PHONY	: all clean fclean re
