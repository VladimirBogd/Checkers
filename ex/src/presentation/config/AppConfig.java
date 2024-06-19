package presentation.config;

import data.repository.GamesRepositoryImpl;
import data.repository.ParticipantRepositoryImpl;
import data.repository.TournamentRepositoryImpl;
import domain.usecase.GamesUseCase;
import domain.usecase.ParticipantUseCase;
import domain.usecase.TournamentUseCase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AppConfig {
	private static Connection connection;

	static {
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:Kursovik.db");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static ParticipantUseCase provideParticipantUseCase() {
		return new ParticipantUseCase(new ParticipantRepositoryImpl(connection));
	}

	public static TournamentUseCase provideTournamentUseCase() {
		return new TournamentUseCase(new TournamentRepositoryImpl(connection));
	}

	public static GamesUseCase provideGamesUseCase() {
		return new GamesUseCase(new GamesRepositoryImpl(connection));
	}
}
