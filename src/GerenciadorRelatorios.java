import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe GerenciadorRelatorios - gera relatórios do sistema
 */
class GerenciadorRelatorios {
    
    /**
     * Gera agenda do dia especificado
     */
    public static void gerarAgendaDiaria(LocalDate data) {
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║                    AGENDA DIÁRIA                        ║");
        System.out.println("║        Data: " + data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "                                 ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
        
        List<Agendamento> agendamentosDia = Agendamento.listaAgendamentos.stream()
            .filter(a -> a.getDataHora().toLocalDate().equals(data))
            .filter(a -> a.getStatus() != StatusAgendamento.CANCELADO)
            .sorted((a1, a2) -> a1.getDataHora().compareTo(a2.getDataHora()))
            .collect(Collectors.toList());
        
        if (agendamentosDia.isEmpty()) {
            System.out.println("📅 Nenhum agendamento encontrado para esta data.");
            return;
        }
        
        int totalAgendamentos = agendamentosDia.size();
        long realizados = agendamentosDia.stream()
            .filter(a -> a.getStatus() == StatusAgendamento.REALIZADO)
            .count();
        long agendados = agendamentosDia.stream()
            .filter(a -> a.getStatus() == StatusAgendamento.AGENDADO)
            .count();
        
        double faturamentoTotal = agendamentosDia.stream()
            .filter(a -> a.getStatus() == StatusAgendamento.REALIZADO)
            .mapToDouble(a -> a.getServico().preco)
            .sum();
        
        System.out.println("\n📊 RESUMO DO DIA:");
        System.out.println("   • Total de agendamentos: " + totalAgendamentos);
        System.out.println("   • Serviços realizados: " + realizados);
        System.out.println("   • Serviços agendados: " + agendados);
        System.out.println("   • Faturamento do dia: R$ " + String.format("%.2f", faturamentoTotal));
        
        System.out.println("\n🕐 CRONOGRAMA DETALHADO:");
        System.out.println("─────────────────────────────────────────────────────────────");
        
        for (Agendamento agendamento : agendamentosDia) {
            String horario = agendamento.getDataHora().format(DateTimeFormatter.ofPattern("HH:mm"));
            String statusIcon = getStatusIcon(agendamento.getStatus());
            
            System.out.println(statusIcon + " " + horario + " | " + 
                             "Cliente: " + agendamento.getCliente().nome);
            System.out.println("         | Pet: " + agendamento.getPet().nome + 
                             " (" + agendamento.getPet().especie + ")");
            System.out.println("         | Serviço: " + agendamento.getServico().nome + 
                             " (R$ " + agendamento.getServico().preco + ")");
            System.out.println("         | Funcionário: " + agendamento.getFuncionario().getNome());
            System.out.println("         | Espaço: " + agendamento.getEspaco().getNome());
            System.out.println("─────────────────────────────────────────────────────────────");
        }
    }
    
    /**
     * Gera relatório de produtividade por funcionário
     */
    public static void gerarRelatorioPorFuncionario(Usuario funcionario, LocalDate dataInicio, LocalDate dataFim) {
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║              RELATÓRIO POR FUNCIONÁRIO                  ║");
        System.out.println("║   Funcionário: " + String.format("%-40s", funcionario.getNome()) + " ║");
        System.out.println("║   Período: " + dataInicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + 
                         " a " + dataFim.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "                    ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
        
        List<Agendamento> agendamentosFuncionario = Agendamento.listaAgendamentos.stream()
            .filter(a -> a.getFuncionario().equals(funcionario))
            .filter(a -> !a.getDataHora().toLocalDate().isBefore(dataInicio))
            .filter(a -> !a.getDataHora().toLocalDate().isAfter(dataFim))
            .filter(a -> a.getStatus() != StatusAgendamento.CANCELADO)
            .sorted((a1, a2) -> a1.getDataHora().compareTo(a2.getDataHora()))
            .collect(Collectors.toList());
        
        if (agendamentosFuncionario.isEmpty()) {
            System.out.println("📋 Nenhum agendamento encontrado para este funcionário no período especificado.");
            return;
        }
        
        int totalAtendimentos = agendamentosFuncionario.size();
        long servicosRealizados = agendamentosFuncionario.stream()
            .filter(a -> a.getStatus() == StatusAgendamento.REALIZADO)
            .count();
        
        double faturamentoGerado = agendamentosFuncionario.stream()
            .filter(a -> a.getStatus() == StatusAgendamento.REALIZADO)
            .mapToDouble(a -> a.getServico().preco)
            .sum();
        
        // Contagem por tipo de serviço
        System.out.println("\n📈 ANÁLISE DE PRODUTIVIDADE:");
        System.out.println("   • Total de atendimentos: " + totalAtendimentos);
        System.out.println("   • Serviços realizados: " + servicosRealizados);
        System.out.println("   • Taxa de conclusão: " + 
                         String.format("%.1f%%", (servicosRealizados * 100.0 / totalAtendimentos)));
        System.out.println("   • Faturamento gerado: R$ " + String.format("%.2f", faturamentoGerado));
        
        // Análise por categoria de serviço
        System.out.println("\n🏷️  SERVIÇOS POR CATEGORIA:");
        for (CategoriaServico categoria : CategoriaServico.values()) {
            long count = agendamentosFuncionario.stream()
                .filter(a -> a.getServico().categoria == categoria)
                .count();
            if (count > 0) {
                System.out.println("   • " + categoria + ": " + count + " atendimentos");
            }
        }
        
        System.out.println("\n📅 HISTÓRICO DETALHADO:");
        System.out.println("─────────────────────────────────────────────────────────────");
        
        for (Agendamento agendamento : agendamentosFuncionario) {
            String dataHora = agendamento.getDataHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            String statusIcon = getStatusIcon(agendamento.getStatus());
            
            System.out.println(statusIcon + " " + dataHora + " | Cliente: " + agendamento.getCliente().nome);
            System.out.println("                      | Pet: " + agendamento.getPet().nome);
            System.out.println("                      | Serviço: " + agendamento.getServico().nome + 
                             " (R$ " + agendamento.getServico().preco + ")");
            System.out.println("                      | Espaço: " + agendamento.getEspaco().getNome());
            System.out.println("─────────────────────────────────────────────────────────────");
        }
    }
    
    /**
     * RELATÓRIO AVANÇADO: Gera relatório de utilização por espaço
     * Mostra a ocupação e uso de um espaço específico em um período
     * 
     * @param espaco Espaço para análise
     * @param dataInicio Data inicial do período
     * @param dataFim Data final do período
     */
    public static void gerarRelatorioPorEspaco(EspacoServico espaco, LocalDate dataInicio, LocalDate dataFim) {
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║                RELATÓRIO POR ESPAÇO                     ║");
        System.out.println("║   Espaço: " + String.format("%-45s", espaco.getNome()) + " ║");
        System.out.println("║   Tipo: " + String.format("%-47s", espaco.getTipo()) + " ║");
        System.out.println("║   Período: " + dataInicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + 
                         " a " + dataFim.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "                    ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
        
        // Filtra agendamentos do espaço no período
        List<Agendamento> agendamentosEspaco = Agendamento.listaAgendamentos.stream()
            .filter(a -> a.getEspaco().equals(espaco))
            .filter(a -> !a.getDataHora().toLocalDate().isBefore(dataInicio))
            .filter(a -> !a.getDataHora().toLocalDate().isAfter(dataFim))
            .filter(a -> a.getStatus() != StatusAgendamento.CANCELADO)
            .sorted((a1, a2) -> a1.getDataHora().compareTo(a2.getDataHora()))
            .collect(Collectors.toList());
        
        if (agendamentosEspaco.isEmpty()) {
            System.out.println("🏢 Nenhum agendamento encontrado para este espaço no período especificado.");
            return;
        }
        
        // Análise de utilização do espaço
        int totalUsos = agendamentosEspaco.size();
        long servicosRealizados = agendamentosEspaco.stream()
            .filter(a -> a.getStatus() == StatusAgendamento.REALIZADO)
            .count();
        
        double faturamentoEspaco = agendamentosEspaco.stream()
            .filter(a -> a.getStatus() == StatusAgendamento.REALIZADO)
            .mapToDouble(a -> a.getServico().preco)
            .sum();
        
        System.out.println("\n📊 ANÁLISE DE UTILIZAÇÃO:");
        System.out.println("   • Total de agendamentos: " + totalUsos);
        System.out.println("   • Serviços realizados: " + servicosRealizados);
        System.out.println("   • Taxa de utilização: " + 
                         String.format("%.1f%%", (servicosRealizados * 100.0 / totalUsos)));
        System.out.println("   • Receita gerada: R$ " + String.format("%.2f", faturamentoEspaco));
        
        // Top funcionários que mais usaram o espaço
        System.out.println("\n👥 FUNCIONÁRIOS MAIS ATIVOS NESTE ESPAÇO:");
        agendamentosEspaco.stream()
            .collect(Collectors.groupingBy(a -> a.getFuncionario().getNome(), 
                    Collectors.counting()))
            .entrySet().stream()
            .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
            .limit(5)
            .forEach(entry -> System.out.println("   • " + entry.getKey() + ": " + entry.getValue() + " usos"));
        
        // Tipos de serviço mais realizados no espaço
        System.out.println("\n🛍️  SERVIÇOS MAIS REALIZADOS:");
        agendamentosEspaco.stream()
            .collect(Collectors.groupingBy(a -> a.getServico().nome, 
                    Collectors.counting()))
            .entrySet().stream()
            .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
            .limit(5)
            .forEach(entry -> System.out.println("   • " + entry.getKey() + ": " + entry.getValue() + " vezes"));
        
        System.out.println("\n📅 HISTÓRICO DE USO:");
        System.out.println("─────────────────────────────────────────────────────────────");
        
        for (Agendamento agendamento : agendamentosEspaco) {
            String dataHora = agendamento.getDataHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            String statusIcon = getStatusIcon(agendamento.getStatus());
            
            System.out.println(statusIcon + " " + dataHora + " | Funcionário: " + agendamento.getFuncionario().getNome());
            System.out.println("                      | Cliente: " + agendamento.getCliente().nome);
            System.out.println("                      | Pet: " + agendamento.getPet().nome);
            System.out.println("                      | Serviço: " + agendamento.getServico().nome);
            System.out.println("─────────────────────────────────────────────────────────────");
        }
    }
    
    /**
     * Gera relatório geral de estatísticas do sistema
     * Visão macro de todos os agendamentos
     */
    public static void gerarRelatorioGeral() {
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║              RELATÓRIO GERAL DO SISTEMA                 ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
        
        int totalAgendamentos = Agendamento.listaAgendamentos.size();
        
        if (totalAgendamentos == 0) {
            System.out.println("📊 Nenhum agendamento cadastrado no sistema.");
            return;
        }
        
        long realizados = Agendamento.listaAgendamentos.stream()
            .filter(a -> a.getStatus() == StatusAgendamento.REALIZADO)
            .count();
        
        long agendados = Agendamento.listaAgendamentos.stream()
            .filter(a -> a.getStatus() == StatusAgendamento.AGENDADO)
            .count();
        
        long cancelados = Agendamento.listaAgendamentos.stream()
            .filter(a -> a.getStatus() == StatusAgendamento.CANCELADO)
            .count();
        
        double faturamentoTotal = Agendamento.listaAgendamentos.stream()
            .filter(a -> a.getStatus() == StatusAgendamento.REALIZADO)
            .mapToDouble(a -> a.getServico().preco)
            .sum();
        
        System.out.println("\n📈 ESTATÍSTICAS GERAIS:");
        System.out.println("   • Total de agendamentos: " + totalAgendamentos);
        System.out.println("   • Serviços realizados: " + realizados);
        System.out.println("   • Serviços agendados: " + agendados);
        System.out.println("   • Serviços cancelados: " + cancelados);
        System.out.println("   • Taxa de sucesso: " + 
                         String.format("%.1f%%", (realizados * 100.0 / totalAgendamentos)));
        System.out.println("   • Faturamento total: R$ " + String.format("%.2f", faturamentoTotal));
        
        // Serviços mais populares
        System.out.println("\n🏆 TOP 5 SERVIÇOS MAIS SOLICITADOS:");
        Agendamento.listaAgendamentos.stream()
            .filter(a -> a.getStatus() != StatusAgendamento.CANCELADO)
            .collect(Collectors.groupingBy(a -> a.getServico().nome, 
                    Collectors.counting()))
            .entrySet().stream()
            .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
            .limit(5)
            .forEach(entry -> System.out.println("   " + entry.getValue() + "x - " + entry.getKey()));
        
        // Funcionários mais produtivos
        System.out.println("\n⭐ TOP 5 FUNCIONÁRIOS MAIS PRODUTIVOS:");
        Agendamento.listaAgendamentos.stream()
            .filter(a -> a.getStatus() == StatusAgendamento.REALIZADO)
            .collect(Collectors.groupingBy(a -> a.getFuncionario().getNome(), 
                    Collectors.counting()))
            .entrySet().stream()
            .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
            .limit(5)
            .forEach(entry -> System.out.println("   " + entry.getValue() + " serviços - " + entry.getKey()));
    }
    
    /**
     * Método auxiliar para obter ícone visual baseado no status
     * 
     * @param status Status do agendamento
     * @return String com ícone correspondente
     */
    private static String getStatusIcon(StatusAgendamento status) {
        switch (status) {
            case AGENDADO:
                return "🕐";
            case REALIZADO:
                return "✅";
            case CANCELADO:
                return "❌";
            case EM_ANDAMENTO:
                return "🔄";
            default:
                return "❓";
        }
    }
    
    /**
     * Lista próximos agendamentos (próximas 24 horas)
     * Útil para visualização rápida da agenda
     */
    public static void listarProximosAgendamentos() {
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║               PRÓXIMOS AGENDAMENTOS                     ║");
        System.out.println("║                  (Próximas 24h)                         ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
        
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime limite = agora.plusHours(24);
        
        List<Agendamento> proximosAgendamentos = Agendamento.listaAgendamentos.stream()
            .filter(a -> a.getStatus() == StatusAgendamento.AGENDADO)
            .filter(a -> a.getDataHora().isAfter(agora) && a.getDataHora().isBefore(limite))
            .sorted((a1, a2) -> a1.getDataHora().compareTo(a2.getDataHora()))
            .collect(Collectors.toList());
        
        if (proximosAgendamentos.isEmpty()) {
            System.out.println("📅 Nenhum agendamento nas próximas 24 horas.");
            return;
        }
        
        System.out.println("\n🔔 " + proximosAgendamentos.size() + " agendamento(s) encontrado(s):");
        System.out.println("─────────────────────────────────────────────────────────────");
        
        for (Agendamento agendamento : proximosAgendamentos) {
            String dataHora = agendamento.getDataHora().format(DateTimeFormatter.ofPattern("dd/MM HH:mm"));
            System.out.println("🕐 " + dataHora + " | " + agendamento.getCliente().nome + 
                             " - " + agendamento.getPet().nome);
            System.out.println("             | " + agendamento.getServico().nome + 
                             " | " + agendamento.getFuncionario().getNome());
            System.out.println("─────────────────────────────────────────────────────────────");
        }
    }
}