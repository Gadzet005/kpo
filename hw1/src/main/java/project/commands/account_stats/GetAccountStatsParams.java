package project.commands.account_stats;

import java.util.Date;

public record GetAccountStatsParams(int accountId, Date start, Date end) {
}