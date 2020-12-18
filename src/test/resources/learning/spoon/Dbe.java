package lerning.spoon;

import de.proplant.duengeportal.dbe.service.domain.out.DbeCalculationResult;
import de.proplant.duengeportal.dbe.service.domain.valueobject.parameter.n.*;
import de.proplant.duengeportal.dbe.service.domain.valueobject.parameter.p.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@Getter
@RequiredArgsConstructor
public class Dbe implements IDbe {

    private final DbeP dbeP;
    private final DbeN dbeN;
    private final DbeContext dbeContext;

    private final IChangeDbeInputParameter changeInput;

    private DbeOptions options;

    @Override
    public Integer getUsageNumber() {
        return dbeContext.getUsage().getNumber();
    }

    @Override
    public IDbe with(Consumer<DbeContext> consumer) {
        consumer.accept(dbeContext);
        //TODO: dbeContext.validate()
        dbeP.init(dbeContext);
        dbeN.init(dbeContext);
        options = new DbeOptions(){
            {
                addOption(CropIdZwischenFrucht.class, () -> dbeContext.getPlot().getIDsOfZwischenFruechteOfUsage(dbeContext.getUsage()));
                addOption(CropIdErnteResteGemuese.class, () -> dbeContext.getPlot().getIDsOfErnteresteAnbaujahrFuerGemueseOfUsage(dbeContext.getUsage()));
                addOption(CropIdPreYear.class, () -> dbeContext.getPlot().getIDsOfVorjahresFruechteOfUsage(dbeContext.getUsage()));
                addOption(ErnteReste.class, () -> dbeContext.getPlot().getErnteresteAuswahlOfHarvest());
                addOption(NMinType.class, () -> getNMinTypeOptionList());
            }
        };
        return this;
    }

    @Override
    public IDbe changeInputsN(
            NErtragsNiveau ertragsNiveau,
            NMin nMin,
            NMinAelterAls4Wochen nMinAelterAls4Wochen,
            NMinType nMinType,
            HumusBoden humusBoden,
            OrganischeDuengungVorjahre organischeDuengungVorjahre,
            CropIdPreYear cropIdPreYear,
            CropIdZwischenFrucht cropIdZwischenFrucht,
            CropIdErnteResteGemuese cropIdErnteResteGemuese,
            RohProtein rohProtein,
            FolieVlies folieVlies,
            LeguminoseContent leguminoseContent,
            NBemerkung bemerkung) {

        return changeInput.changeInputParameterN(
                this,
                ertragsNiveau,
                nMin,
                nMinAelterAls4Wochen,
                nMinType,
                humusBoden,
                organischeDuengungVorjahre,
                cropIdPreYear,
                cropIdZwischenFrucht,
                cropIdErnteResteGemuese,
                rohProtein,
                folieVlies,
                leguminoseContent,
                bemerkung);

    }

    @Override
    public IDbe changeResultNParameter(NBedarfswert nBedarfswert,
                                       ErtragsDifferenz ertragsDifferenz,
                                       CorrNMin corrNMin,
                                       CorrHumus corrHumus,
                                       CorrOrganischeDuengungVorjahre corrOrganischeDuengungVorjahre,
                                       CorrVorjahresFrucht corrVorjahresFrucht,
                                       CorrCatch corrCatch,
                                       CorrPreMain corrPreMain,
                                       CorrCover corrCover,
                                       RPDifferenz rpDifferenz,
                                       CorrNBindungLeguminosen corrNBindungLeguminosen,
                                       NDuengeBedarf nDuengeBedarf,
                                       NGesamtBedarf nGesamtBedarf,
                                       NGesamtBedarfMinus20Prozent nGesamtBedarfMinus20Prozent,
                                       NEffektivDuengeBedarf nEffektivDuengeBedarf) {

        dbeN.changeResultParameters(
                nBedarfswert,
                ertragsDifferenz,
                corrNMin,
                corrHumus,
                corrOrganischeDuengungVorjahre,
                corrVorjahresFrucht,
                corrCatch,
                corrPreMain,
                corrCover,
                rpDifferenz,
                corrNBindungLeguminosen,
                nDuengeBedarf,
                nGesamtBedarf,
                nGesamtBedarfMinus20Prozent,
                nEffektivDuengeBedarf);

        return this;
    }

    @Override
    public IDbe changeKorrekturNParameter(
            NachtraeglicheKorrekturNDuengebedarf nachtraeglicheKorrekturNDuengebedarf,
            NDuengeBedarfKorrekturdatum nDuengeBedarfKorrekturdatum,
            BegruendungNachtraeglicheKorrekturNDuengeBedarf begruendungNachtraeglicheKorrekturNDuengeBedarf) {

        dbeN.getNachtraeglicheKorrekturNDuengebedarf().setValue(nachtraeglicheKorrekturNDuengebedarf.getValue());
        dbeN.getNDuengeBedarfKorrekturdatum().setValue(nDuengeBedarfKorrekturdatum.getValue());
        dbeN.getBegruendungNachtraeglicheKorrekturNDuengeBedarf().setValue(begruendungNachtraeglicheKorrekturNDuengeBedarf.getValue());
        return this;
    }

    @Override
    public IDbe changeSystemNParameter(ErtragsNiveauLautDueV ertragsNiveauLautDuev,
                                       RohProteinLautDuev rohProteinLautDuev,
                                       CropDate cropDate) {

        if (dbeN.isPlanned() || dbeN.isCalculated()) {
            return this;
        }
        dbeN.getErtragsNiveauLautDueV().setValue(ertragsNiveauLautDuev.getValue());
        dbeN.getRohProteinLautDuev().setValue(rohProteinLautDuev.getValue());
        dbeN.getCropDate().setValue(cropDate.getValue());
        return this;
    }

    @Override
    public IDbe changeInputsP(ErtragsNiveau ertragsNiveau,
                              ErnteReste ernteReste,
                              PBemerkung bemerkung) {

        return changeInput.changeInputParameterP(
                this,
                ertragsNiveau,
                ernteReste,
                bemerkung);
    }

    @Override
    public IDbe changeResultPParameter(EntzugMainProDt entzugMainProDt,
                                       EntzugMainProHa entzugMainProHa,
                                       EntzugMinorProDt entzugMinorProDt,
                                       EntzugMinorProHa entzugMinorProHa,
                                       EntzugProHa entzugProHa,
                                       EntzugProFlaeche entzugProFlaeche,
                                       P2O5EmpfehlungInProzent p2O5EmpfehlungInProzent,
                                       P2O5EmpfehlungProHa p2O5EmpfehlungProHa,
                                       P2O5EmpfehlungProFlaeche p2O5EmpfehlungProFlaeche,
                                       MinDuengejahr minDuengejahr) {

        dbeP.getEntzugMainProDt().setValue(entzugMainProDt.getValue());
        dbeP.getEntzugMainProHa().setValue(entzugMainProHa.getValue());
        dbeP.getEntzugMinorProDt().setValue(entzugMinorProDt.getValue());
        dbeP.getEntzugMinorProHa().setValue(entzugMinorProHa.getValue());
        dbeP.getEntzugProHa().setValue(entzugProHa.getValue());
        dbeP.getEntzugProFlaeche().setValue(entzugProFlaeche.getValue());
        dbeP.getP2O5EmpfehlungInProzent().setValue(p2O5EmpfehlungInProzent.getValue());
        dbeP.getP2O5EmpfehlungProHa().setValue(p2O5EmpfehlungProHa.getValue());
        dbeP.getP2O5EmpfehlungProFlaeche().setValue(p2O5EmpfehlungProFlaeche.getValue());
        dbeP.getMinDuengejahr().setValue(minDuengejahr.getValue());
        return this;
    }

    @Override
    public IDbe changeSystemPParameter(AgriCulturalUsageSize agriCulturalUsageSize,
                                       CropRefMainP cropRefMainP,
                                       CropRefMinorP cropRefMinorP,
                                       P2O5GehaltProbe p2O5GehaltProbe,
                                       P2O5Klasse p2O5Klasse) {

        if (dbeP.isPlanned() || dbeP.isCalculated()) {
            return this;
        }
        dbeP.getAgriCulturalUsageSize().setValue(agriCulturalUsageSize.getValue());
        dbeP.getCropRefMainP().setValue(cropRefMainP.getValue());
        dbeP.getCropRefMinorP().setValue(cropRefMinorP.getValue());
        dbeP.getP2O5GehaltProbe().setValue(p2O5GehaltProbe.getValue());
        dbeP.getP2O5Klasse().setValue(p2O5Klasse.getValue());
        return this;
    }

    @Override
    public IDbe calculate() {

        if (dbeN.isPlanned() || dbeN.isCalculated()) {
            dbeN.calculate();
        }
        if (dbeP.isPlanned() || dbeP.isCalculated()) {
            dbeP.calculate();
        }
        return this;
    }


    @Override
    public List<Object> getOptions(String parameterName) {

        return options.getOptionAsList(parameterName);
    }

    @Override
    public IDbe withNStatus(DbeStatus nStatus) {
        dbeN.initStatus(nStatus);
        return this;
    }

    @Override
    public IDbe withPStatus(DbeStatus pStatus) {
        dbeP.initStatus(pStatus);
        return this;
    }

    @Override
    public boolean confirmDbeN(LocalDate date) {
        return dbeN.confirm(date);
    }

    @Override
    public boolean confirmDbeP(LocalDate date) {
        return dbeP.confirm(date);
    }

    @Override
    public boolean correctDbeN(String reason) {
        return dbeN.correct(reason);
    }

    @Override
    public boolean correctDbeP(String reason) {
        return dbeP.correct(reason);
    }


    @Override
    public boolean resetDbeN() {
        return dbeN.reset();
    }

    @Override
    public boolean resetDbeP() {
        return dbeP.reset();
    }

    //das ist sind die berechneten Zuschläge
    @Override
    public DbeCalculationResult getResult() {

        return new DbeCalculationResult(
                getDbeN().getErtragsDifferenz().value,
                getDbeN().getRpDifferenz().value,
                getDbeN().getCorrCatch().calculate(),
                getDbeN().getCorrCover().calculate(),
                getDbeN().getCorrHumus().value,
                getDbeN().getCorrNBindungLeguminosen().value,
                getDbeN().getCorrNMin().value,
                getDbeN().getCorrOrganischeDuengungVorjahre().value,
                getDbeN().getCorrPreMain().calculate(),
                getDbeN().getCorrVorjahresFrucht().value,
                getDbeN().getNDuengeBedarf().value,
                dbeP.getEntzugMainProHa().getValue(),
                dbeP.getEntzugMinorProHa().getValue(),
                dbeP.getEntzugProHa().getValue(),
                dbeP.getP2O5Gehalt().getValue(),
                dbeP.getP2O5EmpfehlungProHa().getValue(),
                getDbeN().getRpDifferenz().value
        );
    }

    /**
     *
     *
     * @return list with options
     */
    public List<String> getNMinTypeOptionList() {

        return Arrays.asList(NMinType.LABORWERT, NMinType.RICHTWERT);
    }

    public boolean plan() {
        var isDbeNPlanned = this.getDbeN().getStatus().plan();
        var isDbePPlanned = this.getDbeP().getStatus().plan();
        return isDbeNPlanned || isDbePPlanned;
    }

}
